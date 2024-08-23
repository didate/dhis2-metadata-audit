import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OptionGroupDetailComponent } from './option-group-detail.component';

describe('OptionGroup Management Detail Component', () => {
  let comp: OptionGroupDetailComponent;
  let fixture: ComponentFixture<OptionGroupDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OptionGroupDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: OptionGroupDetailComponent,
              resolve: { optionGroup: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OptionGroupDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OptionGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load optionGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OptionGroupDetailComponent);

      // THEN
      expect(instance.optionGroup()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
