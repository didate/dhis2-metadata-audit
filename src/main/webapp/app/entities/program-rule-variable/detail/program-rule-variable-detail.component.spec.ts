import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProgramRuleVariableDetailComponent } from './program-rule-variable-detail.component';

describe('ProgramRuleVariable Management Detail Component', () => {
  let comp: ProgramRuleVariableDetailComponent;
  let fixture: ComponentFixture<ProgramRuleVariableDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgramRuleVariableDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProgramRuleVariableDetailComponent,
              resolve: { programRuleVariable: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProgramRuleVariableDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgramRuleVariableDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programRuleVariable on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProgramRuleVariableDetailComponent);

      // THEN
      expect(instance.programRuleVariable()).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
