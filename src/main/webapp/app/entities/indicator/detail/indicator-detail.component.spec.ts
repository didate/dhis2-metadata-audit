import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { IndicatorDetailComponent } from './indicator-detail.component';

describe('Indicator Management Detail Component', () => {
  let comp: IndicatorDetailComponent;
  let fixture: ComponentFixture<IndicatorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndicatorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IndicatorDetailComponent,
              resolve: { indicator: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IndicatorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load indicator on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IndicatorDetailComponent);

      // THEN
      expect(instance.indicator()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
