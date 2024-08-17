import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PersonNotifierDetailComponent } from './person-notifier-detail.component';

describe('PersonNotifier Management Detail Component', () => {
  let comp: PersonNotifierDetailComponent;
  let fixture: ComponentFixture<PersonNotifierDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonNotifierDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PersonNotifierDetailComponent,
              resolve: { personNotifier: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PersonNotifierDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonNotifierDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personNotifier on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PersonNotifierDetailComponent);

      // THEN
      expect(instance.personNotifier()).toEqual(expect.objectContaining({ id: 123 }));
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
