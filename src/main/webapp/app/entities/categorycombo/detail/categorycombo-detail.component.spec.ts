import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CategorycomboDetailComponent } from './categorycombo-detail.component';

describe('Categorycombo Management Detail Component', () => {
  let comp: CategorycomboDetailComponent;
  let fixture: ComponentFixture<CategorycomboDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategorycomboDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CategorycomboDetailComponent,
              resolve: { categorycombo: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CategorycomboDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategorycomboDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categorycombo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CategorycomboDetailComponent);

      // THEN
      expect(instance.categorycombo()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
