import { Component } from '@angular/core';
import { QuizService } from '../../shared/services/quiz.service';
import { map, Observable } from 'rxjs';
import { PlayerAnswer, Question } from '../../shared/models/quiz.model';
import { ReactiveFormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { QuestionComponent } from '../question/question.component';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [AsyncPipe, QuestionComponent, ReactiveFormsModule],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.scss',
})
export class QuizComponent {
  questions$: Observable<Question[]> = this.quizService.questions$;
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

  onSubmitAnswers(questionId: string, answers: string[]) {
    this._selectedAnswers.push({ questionId, answers });
    console.log('Selected answers:', this._selectedAnswers);
  }
}
