import { TestBed } from '@angular/core/testing';

import { QuizMessageService } from './quiz-message.service';

describe('QuizMessageService', () => {
  let service: QuizMessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuizMessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
