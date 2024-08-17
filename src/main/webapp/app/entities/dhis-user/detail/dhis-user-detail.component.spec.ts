import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DHISUserDetailComponent } from './dhis-user-detail.component';

describe('DHISUser Management Detail Component', () => {
  let comp: DHISUserDetailComponent;
  let fixture: ComponentFixture<DHISUserDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DHISUserDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DHISUserDetailComponent,
              resolve: { dHISUser: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DHISUserDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DHISUserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dHISUser on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DHISUserDetailComponent);

      // THEN
      expect(instance.dHISUser()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
