import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OptionGroupHistoryComponent } from './option-group-history.component';

describe('OptionGroupHistoryComponent', () => {
  let component: OptionGroupHistoryComponent;
  let fixture: ComponentFixture<OptionGroupHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OptionGroupHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OptionGroupHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
