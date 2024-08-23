import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganisationUnitFormService } from './organisation-unit-form.service';
import { OrganisationUnitService } from '../service/organisation-unit.service';
import { IOrganisationUnit } from '../organisation-unit.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';

import { OrganisationUnitUpdateComponent } from './organisation-unit-update.component';

describe('OrganisationUnit Management Update Component', () => {
  let comp: OrganisationUnitUpdateComponent;
  let fixture: ComponentFixture<OrganisationUnitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organisationUnitFormService: OrganisationUnitFormService;
  let organisationUnitService: OrganisationUnitService;
  let dHISUserService: DHISUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganisationUnitUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(OrganisationUnitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganisationUnitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organisationUnitFormService = TestBed.inject(OrganisationUnitFormService);
    organisationUnitService = TestBed.inject(OrganisationUnitService);
    dHISUserService = TestBed.inject(DHISUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DHISUser query and add missing value', () => {
      const organisationUnit: IOrganisationUnit = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '15c9b95c-201f-4555-8ca6-c74538a0acaa' };
      organisationUnit.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '02825a20-dfba-48a0-8e6e-790300a1a4fd' };
      organisationUnit.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '150e83cc-9495-442e-953b-2db8aa7a64c8' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organisationUnit: IOrganisationUnit = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '6f7683c1-9e64-4a46-8e36-e43c89411282' };
      organisationUnit.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '8e6e40ee-5f6f-439e-ac41-81227e7106a3' };
      organisationUnit.lastUpdatedBy = lastUpdatedBy;

      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.organisationUnit).toEqual(organisationUnit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganisationUnit>>();
      const organisationUnit = { id: 'ABC' };
      jest.spyOn(organisationUnitFormService, 'getOrganisationUnit').mockReturnValue(organisationUnit);
      jest.spyOn(organisationUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organisationUnit }));
      saveSubject.complete();

      // THEN
      expect(organisationUnitFormService.getOrganisationUnit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organisationUnitService.update).toHaveBeenCalledWith(expect.objectContaining(organisationUnit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganisationUnit>>();
      const organisationUnit = { id: 'ABC' };
      jest.spyOn(organisationUnitFormService, 'getOrganisationUnit').mockReturnValue({ id: null });
      jest.spyOn(organisationUnitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organisationUnit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organisationUnit }));
      saveSubject.complete();

      // THEN
      expect(organisationUnitFormService.getOrganisationUnit).toHaveBeenCalled();
      expect(organisationUnitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganisationUnit>>();
      const organisationUnit = { id: 'ABC' };
      jest.spyOn(organisationUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organisationUnitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDHISUser', () => {
      it('Should forward to dHISUserService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(dHISUserService, 'compareDHISUser');
        comp.compareDHISUser(entity, entity2);
        expect(dHISUserService.compareDHISUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
