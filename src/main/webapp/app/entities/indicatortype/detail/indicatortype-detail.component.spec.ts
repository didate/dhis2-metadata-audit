import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { IndicatortypeDetailComponent } from './indicatortype-detail.component';

describe('Indicatortype Management Detail Component', () => {
  let comp: IndicatortypeDetailComponent;
  let fixture: ComponentFixture<IndicatortypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndicatortypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IndicatortypeDetailComponent,
              resolve: { indicatortype: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IndicatortypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicatortypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load indicatortype on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IndicatortypeDetailComponent);

      // THEN
      expect(instance.indicatortype()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
