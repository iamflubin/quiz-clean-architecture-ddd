import { Component, Input } from '@angular/core';
import { CompletedSession } from '../../shared/models/quiz.model';
import { QuizMessageService } from '../../shared/services/quiz-message.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-results',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './results.component.html',
  styleUrl: './results.component.scss',
})
export class ResultsComponent {
  @Input({ required: true }) results: CompletedSession = {
    score: 70,
    questions: [
      {
        text: 'What is the chemical symbol for water?',
        answers: ['H2O', 'O2', 'CO2', 'H2'],
        correctAnswers: ['H2O'],
        correct: true,
      },
      {
        text: 'Which planet is known as the Red Planet?',
        answers: ['Mars', 'Venus', 'Jupiter', 'Saturn'],
        correctAnswers: ['Mars'],
        correct: true,
      },
      {
        text: 'What gas do plants absorb from the atmosphere?',
        answers: ['Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Helium'],
        correctAnswers: ['Carbon Dioxide'],
        correct: true,
      },
      {
        text: 'What force keeps us on the ground?',
        answers: ['Magnetism', 'Friction', 'Gravity', 'Tension'],
        correctAnswers: ['Gravity'],
        correct: true,
      },
      {
        text: 'What is the boiling point of water at sea level?',
        answers: ['100°C', '0°C', '50°C', '200°C'],
        correctAnswers: ['100°C'],
        correct: true,
      },
      {
        text: 'Which of these animals is a mammal?',
        answers: ['Shark', 'Dolphin', 'Crocodile', 'Frog'],
        correctAnswers: ['Dolphin'],
        correct: true,
      },
      {
        text: 'What is the basic unit of life?',
        answers: ['Atom', 'Molecule', 'Cell', 'Organ'],
        correctAnswers: ['Cell'],
        correct: true,
      },
      {
        text: 'Which of the following is NOT a state of matter?',
        answers: ['Solid', 'Liquid', 'Gas', 'Energy'],
        correctAnswers: ['Energy'],
        correct: false,
      },
      {
        text: 'What is the largest planet in our solar system?',
        answers: ['Earth', 'Saturn', 'Jupiter', 'Mars'],
        correctAnswers: ['Jupiter'],
        correct: false,
      },
      {
        text: 'How many continents are there on Earth?',
        answers: ['5', '6', '7', '8'],
        correctAnswers: ['7'],
        correct: false,
      },
    ],
  };

  private _encouragementMessages: Record<string, string> = {};

  constructor(private readonly quizMessageService: QuizMessageService) {}

  get encouragementMessage(): { title: string; subtitle: string } {
    if (!this.results) {
      return { title: '', subtitle: '' };
    }
    const { title, subtitle } = this.quizMessageService.evaluateQuizScore(
      this.results.score
    );

    return { title, subtitle };
  }
}
