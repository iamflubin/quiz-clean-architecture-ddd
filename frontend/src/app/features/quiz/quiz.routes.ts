import { Route } from '@angular/router';
import { SessionFormComponent } from './components/session-form/session-form.component';
import { QuizComponent } from './components/quiz/quiz.component';

export const quizRoutes: Route[] = [
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
    component: QuizComponent,
  },
];
