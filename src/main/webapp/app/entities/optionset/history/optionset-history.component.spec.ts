import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OptionsetHistoryComponent } from './optionset-history.component';

describe('OptionsetHistoryComponent', () => {
  let component: OptionsetHistoryComponent;
  let fixture: ComponentFixture<OptionsetHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OptionsetHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OptionsetHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
