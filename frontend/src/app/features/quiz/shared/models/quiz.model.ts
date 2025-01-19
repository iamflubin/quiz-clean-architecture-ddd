export interface Question {
  id: string;
  text: string;
  answers: string[];
}

export interface PlayerAnswer {
  questionId: string;
  answers: string[];
}

export type DifficultyLevel = 'EASY' | 'MEDIUM' | 'HARD';

export type Category =
  | 'GENERAL_KNOWLEDGE'
  | 'SCIENCE'
  | 'SPORTS'
  | 'POP_CULTURE'
  | 'LITERATURE_MYTHOLOGY'
  | 'FOOD_DRINK'
  | 'ENTERTAINMENT';
