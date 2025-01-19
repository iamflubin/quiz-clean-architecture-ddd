import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Category,
  CompletedSession,
  DifficultyLevel,
  Question,
  Session,
} from '../models/quiz.model';
import {
  BehaviorSubject,
  filter,
  finalize,
  map,
  Observable,
  switchMap,
  tap,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  private readonly _loading$: BehaviorSubject<boolean> =
    new BehaviorSubject<boolean>(false);

  private _session: BehaviorSubject<Session | null> =
    new BehaviorSubject<Session | null>(null);

  private _result: BehaviorSubject<CompletedSession | null> =
    new BehaviorSubject<CompletedSession | null>(null);

  /**
   private _questions: BehaviorSubject<Question[]> = new BehaviorSubject<
   Question[]
   >([
   {
   id: 'q1',
   text: 'What is the capital of France?',
   answers: ['Berlin', 'Madrid', 'Paris', 'Rome'],
   },
   {
   id: 'q2',
   text: 'Which planet is known as the Red Planet?',
   answers: ['Earth', 'Mars', 'Venus', 'Jupiter'],
   },
   {
   id: 'q3',
   text: 'Who painted the Mona Lisa?',
   answers: [
   'Vincent van Gogh',
   'Pablo Picasso',
   'Leonardo da Vinci',
   'Claude Monet',
   ],
   },
   {
   id: 'q4',
   text: 'What is the smallest prime number?',
   answers: ['1', '2', '3', '5'],
   },
   {
   id: 'q5',
   text: 'How many continents are there on Earth?',
   answers: ['5', '6', '7', '8'],
   },
   {
   id: 'q6',
   text: 'What is the chemical symbol for water?',
   answers: ['H2O', 'CO2', 'O2', 'HO2'],
   },
   {
   id: 'q7',
   text: 'Which language is primarily used for Android app development?',
   answers: ['Java', 'Swift', 'Python', 'Kotlin'],
   },
   {
   id: 'q8',
   text: 'Which element has the chemical symbol "Fe"?',
   answers: ['Iron', 'Gold', 'Silver', 'Copper'],
   },
   {
   id: 'q9',
   text: 'What is the tallest mountain in the world?',
   answers: ['Mount Everest', 'K2', 'Kangchenjunga', 'Lhotse'],
   },
   {
   id: 'q10',
   text: 'What year did World War II end?',
   answers: ['1943', '1945', '1950', '1939'],
   },
   ]);*/

  constructor(private readonly http: HttpClient) {}

  get loading$(): Observable<boolean> {
    return this._loading$.asObservable();
  }

  get questions$(): Observable<Question[]> {
    return this._session.pipe(map(session => session?.questions ?? []));
  }

  get result$(): Observable<CompletedSession | null> {
    return this._result.asObservable();
  }

  startSession(
    difficultyLevel: DifficultyLevel,
    category: Category
  ): Observable<Session> {
    this._loading$.next(true);
    return this.http
      .post<Session>('/api/v1/sessions', {
        difficultyLevel,
        category,
      })
      .pipe(
        tap(session => {
          this._session.next(session);
        }),
        finalize(() => this._loading$.next(false))
      );
  }

  completeSession(answers: string[][]): Observable<CompletedSession> {
    this._loading$.next(true);
    return this._session.pipe(
      filter(session => session !== null),
      switchMap(session => {
        return this.http
          .post<CompletedSession>(`/api/v1/sessions/${session.id}/complete`, {
            answers,
          })
          .pipe(
            tap(result => {
              this._result.next(result);
            })
          );
      }),
      finalize(() => this._loading$.next(false))
    );
  }
}
