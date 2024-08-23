import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TrackedEntityAttributeDetailComponent } from './tracked-entity-attribute-detail.component';

describe('TrackedEntityAttribute Management Detail Component', () => {
  let comp: TrackedEntityAttributeDetailComponent;
  let fixture: ComponentFixture<TrackedEntityAttributeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrackedEntityAttributeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TrackedEntityAttributeDetailComponent,
              resolve: { trackedEntityAttribute: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TrackedEntityAttributeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackedEntityAttributeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trackedEntityAttribute on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TrackedEntityAttributeDetailComponent);

      // THEN
      expect(instance.trackedEntityAttribute()).toEqual(expect.objectContaining({ id: 'ABC' }));
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
