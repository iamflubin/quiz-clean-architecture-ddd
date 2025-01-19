import { Injectable } from '@angular/core';

interface Rule {
  minScore: number;
  maxScore: number;
  title: string;
  subtitle: string;
  isApplicable: (score: number) => boolean;
}

@Injectable({
  providedIn: 'root',
})
export class QuizMessageService {
  private readonly _rules: Rule[] = [
    {
      minScore: 90,
      maxScore: 100,
      title: '🏆 Incredible! You aced it!',
      subtitle:
        "You're a true quiz master! Challenge yourself with tougher quizzes.",
      isApplicable: (score: number) => score >= 90 && score <= 100,
    },
    {
      minScore: 80,
      maxScore: 89,
      title: '🌟 Great job! Almost perfect!',
      subtitle:
        "You're so close to perfection! A little more practice and you'll master it.",
      isApplicable: (score: number) => score >= 80 && score <= 89,
    },
    {
      minScore: 60,
      maxScore: 79,
      title: "👍 Nice work! You're getting there!",
      subtitle: "You're doing well, but there's room for improvement.",
      isApplicable: (score: number) => score >= 60 && score <= 79,
    },
    {
      minScore: 40,
      maxScore: 59,
      title: '📚 Keep Practicing!',
      subtitle:
        "You're learning, and that's what matters! Try again and improve.",
      isApplicable: (score: number) => score >= 40 && score <= 59,
    },
    {
      minScore: 20,
      maxScore: 39,
      title: "💡 Don't Give Up!",
      subtitle:
        'Mistakes help us learn. Review the questions and give it another shot.',
      isApplicable: (score: number) => score >= 20 && score <= 39,
    },
    {
      minScore: 0,
      maxScore: 19,
      title: '😞 Oops! Better luck next time!',
      subtitle: "It's okay to struggle, learning takes time. Keep trying!",
      isApplicable: (score: number) => score >= 0 && score <= 19,
    },
  ];

  evaluateQuizScore(score: number): { title: string; subtitle: string } {
    const applicableRule = this._rules.find(rule => rule.isApplicable(score));
    if (!applicableRule) {
      throw new Error('No applicable rule found');
    }
    return {
      title: applicableRule.title,
      subtitle: applicableRule.subtitle,
    };
  }
}
