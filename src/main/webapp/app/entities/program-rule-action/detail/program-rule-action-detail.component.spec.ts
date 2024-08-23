import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProgramRuleActionDetailComponent } from './program-rule-action-detail.component';

describe('ProgramRuleAction Management Detail Component', () => {
  let comp: ProgramRuleActionDetailComponent;
  let fixture: ComponentFixture<ProgramRuleActionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgramRuleActionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProgramRuleActionDetailComponent,
              resolve: { programRuleAction: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProgramRuleActionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgramRuleActionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programRuleAction on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProgramRuleActionDetailComponent);

      // THEN
      expect(instance.programRuleAction()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
