import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { atLeastOneCheckboxCheckedValidator } from '../../shared/validators';

@Component({
  selector: 'app-question',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './question.component.html',
  styleUrl: './question.component.scss',
})
export class QuestionComponent implements OnChanges {
  @Input({ required: true }) text = '';
  @Input({ required: true }) answers: string[] = [];
  @Input() last = false;
  @Input() loading = false;
  @Output() selectAnswers = new EventEmitter<string[]>();
  @Output() submitAnswers = new EventEmitter<string[]>();
  form: FormGroup;

  constructor(private readonly fb: FormBuilder) {
    this.form = this.fb.group({
      checkboxes: this.fb.array([], atLeastOneCheckboxCheckedValidator),
    });
  }

  get buttonLabel(): string {
    if (this.loading) {
      return 'Calculating Score ... ⏳';
    }
    return this.last ? 'Submit' : 'Next';
  }

  get checkboxes(): FormArray {
    return this.form.get('checkboxes') as FormArray;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['answers']) {
      const controls = this.answers.map(() => this.fb.control(false));
      this.form.setControl(
        'checkboxes',
        this.fb.array(controls, atLeastOneCheckboxCheckedValidator)
      );
    }
  }

  onSubmit() {
    if (this.form.invalid) {
      return;
    }
    const selectedAnswers = this.answers.filter(
      (_, i) => this.checkboxes.at(i).value
    );
    if (this.last) {
      this.submitAnswers.emit(selectedAnswers);
    } else {
      this.selectAnswers.emit(selectedAnswers);
    }
  }
}
