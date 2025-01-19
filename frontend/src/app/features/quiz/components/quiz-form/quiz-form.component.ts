import { Component } from '@angular/core';
import { QuizService } from '../../shared/services/quiz.service';
import { map, Observable } from 'rxjs';
import { PlayerAnswer } from '../../shared/models/quiz.model';
import { ReactiveFormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { QuestionComponent } from '../question/question.component';
import { ResultsComponent } from '../results/results.component';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    AsyncPipe,
    QuestionComponent,
    ReactiveFormsModule,
    ResultsComponent,
  ],
  templateUrl: './quiz-form.component.html',
  styleUrl: './quiz-form.component.scss',
})
export class QuizFormComponent {
  questions$ = this.quizService.questions$;
  loading$ = this.quizService.loading$;
  result$ = this.quizService.result$;
  currentIndex = 0;
  private _selectedAnswers: PlayerAnswer[] = [];

  constructor(private readonly quizService: QuizService) {}

  get currentQuestion(): number {
    return this.currentIndex + 1;
  }

  get currentProgress$(): Observable<number> {
    return this.questions$.pipe(
      map(questions => (this.currentQuestion / questions.length) * 100)
    );
  }

  get questionCount$(): Observable<number> {
    return this.questions$.pipe(map(questions => questions.length));
  }

  onSelectAnswers(questionId: string, answers: string[]) {
    this._selectedAnswers.push({ questionId, answers });
    this.currentIndex++;
  }

  onSubmitAnswers(questionId: string, selectedAnswers: string[]) {
    this._selectedAnswers.push({ questionId, answers: selectedAnswers });
    const answers: string[][] = this._selectedAnswers.map(
      answer => answer.answers
    );
    this.quizService.completeSession(answers).subscribe();
    console.log('Selected answers:', this._selectedAnswers);
  }
}
