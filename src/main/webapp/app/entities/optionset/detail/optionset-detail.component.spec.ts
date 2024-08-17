import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OptionsetDetailComponent } from './optionset-detail.component';

describe('Optionset Management Detail Component', () => {
  let comp: OptionsetDetailComponent;
  let fixture: ComponentFixture<OptionsetDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OptionsetDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: OptionsetDetailComponent,
              resolve: { optionset: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OptionsetDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OptionsetDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load optionset on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OptionsetDetailComponent);

      // THEN
      expect(instance.optionset()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
