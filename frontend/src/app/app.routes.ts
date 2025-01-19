import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./features/quiz/quiz.routes').then(m => m.quizRoutes),
  },
];
