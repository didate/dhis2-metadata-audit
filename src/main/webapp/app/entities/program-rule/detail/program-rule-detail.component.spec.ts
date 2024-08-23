import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProgramRuleDetailComponent } from './program-rule-detail.component';

describe('ProgramRule Management Detail Component', () => {
  let comp: ProgramRuleDetailComponent;
  let fixture: ComponentFixture<ProgramRuleDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgramRuleDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProgramRuleDetailComponent,
              resolve: { programRule: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProgramRuleDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgramRuleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programRule on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProgramRuleDetailComponent);

      // THEN
      expect(instance.programRule()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
