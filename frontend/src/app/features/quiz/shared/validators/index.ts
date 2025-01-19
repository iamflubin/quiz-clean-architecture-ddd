import { AbstractControl, FormArray, ValidationErrors } from '@angular/forms';

export function atLeastOneCheckboxCheckedValidator(
  control: AbstractControl
): ValidationErrors | null {
  const formArray = control as FormArray;
  const isAtLeastOneChecked = formArray.controls.some(
    checkbox => checkbox.value === true
  );

  return isAtLeastOneChecked ? null : { atLeastOneChecked: true };
}
