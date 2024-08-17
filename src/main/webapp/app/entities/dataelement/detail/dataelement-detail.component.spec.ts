import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataelementDetailComponent } from './dataelement-detail.component';

describe('Dataelement Management Detail Component', () => {
  let comp: DataelementDetailComponent;
  let fixture: ComponentFixture<DataelementDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataelementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DataelementDetailComponent,
              resolve: { dataelement: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DataelementDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DataelementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dataelement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DataelementDetailComponent);

      // THEN
      expect(instance.dataelement()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
