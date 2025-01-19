import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  Option,
  SelectComponent,
} from '../../../../shared/components/select/select.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { QuizService } from '../../shared/services/quiz.service';
import { Subject, takeUntil } from 'rxjs';
import { NgxSpinnerComponent, NgxSpinnerService } from 'ngx-spinner';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-quiz-form',
  standalone: true,
  imports: [
    SelectComponent,
    NgxSpinnerComponent,
    AsyncPipe,
    ReactiveFormsModule,
  ],
  templateUrl: './session-form.component.html',
  styleUrl: './session-form.component.scss',
})
export class SessionFormComponent implements OnInit, OnDestroy {
  readonly difficultyLevels: Option[] = [
    { value: 'EASY', label: 'Easy' },
    { value: 'MEDIUM', label: 'Medium' },
    { value: 'HARD', label: 'Hard' },
  ];

  readonly categories: Option[] = [
    { value: 'GENERAL_KNOWLEDGE', label: 'General Knowledge' },
    { value: 'SCIENCE', label: 'Science' },
    { value: 'SPORTS', label: 'Sports' },
    { value: 'POP_CULTURE', label: 'Pop Culture' },
    { value: 'LITERATURE_MYTHOLOGY', label: 'Literature & Mythology' },
    { value: 'FOOD_DRINK', label: 'Food & Drink' },
    { value: 'ENTERTAINMENT', label: 'Entertainment' },
  ];

  form: FormGroup;
  loading$ = this.quizService.loading$;

  private destroy$ = new Subject<void>();

  constructor(
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly quizService: QuizService,
    private readonly spinnerService: NgxSpinnerService
  ) {
    this.form = this.fb.group({
      difficultyLevel: ['EASY', Validators.required],
      category: ['GENERAL_KNOWLEDGE', Validators.required],
    });
  }

  ngOnInit() {
    this.spinnerService.show();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  onCreateSession() {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      return;
    }

    this.quizService
      .startSession(this.form.value.difficultyLevel, this.form.value.category)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => this.router.navigate(['quiz']));
  }
}
