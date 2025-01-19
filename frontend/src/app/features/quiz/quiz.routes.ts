import { Route } from '@angular/router';
import { SessionFormComponent } from './components/session-form/session-form.component';
import { QuizFormComponent } from './components/quiz-form/quiz-form.component';
import { QuizComponent } from './quiz.component';

export const quizRoutes: Route[] = [
  {
    path: '',
    component: QuizComponent,
    children: [
      {
        path: '',
        redirectTo: 'create-session',
        pathMatch: 'full',
      },
      {
        path: 'create-session',
        component: SessionFormComponent,
      },
      {
        path: 'quiz',
        component: QuizFormComponent,
      },
    ],
  },
];
