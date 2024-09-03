import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategorycomboHistoryComponent } from './categorycombo-history.component';

describe('CategorycomboHistoryComponent', () => {
  let component: CategorycomboHistoryComponent;
  let fixture: ComponentFixture<CategorycomboHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategorycomboHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CategorycomboHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
