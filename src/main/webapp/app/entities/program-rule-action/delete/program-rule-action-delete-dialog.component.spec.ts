jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ProgramRuleActionService } from '../service/program-rule-action.service';

import { ProgramRuleActionDeleteDialogComponent } from './program-rule-action-delete-dialog.component';

describe('ProgramRuleAction Management Delete Component', () => {
  let comp: ProgramRuleActionDeleteDialogComponent;
  let fixture: ComponentFixture<ProgramRuleActionDeleteDialogComponent>;
  let service: ProgramRuleActionService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProgramRuleActionDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ProgramRuleActionDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgramRuleActionDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProgramRuleActionService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
