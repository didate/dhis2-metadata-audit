import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackedEntityAttributeHistoryComponent } from './tracked-entity-attribute-history.component';

describe('TrackedEntityAttributeHistoryComponent', () => {
  let component: TrackedEntityAttributeHistoryComponent;
  let fixture: ComponentFixture<TrackedEntityAttributeHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TrackedEntityAttributeHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TrackedEntityAttributeHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
