import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'project',
        data: { pageTitle: 'dhis2TrackApp.project.home.title' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'person-notifier',
        data: { pageTitle: 'dhis2TrackApp.personNotifier.home.title' },
        loadChildren: () => import('./person-notifier/person-notifier.module').then(m => m.PersonNotifierModule),
      },
      {
        path: 'indicatortype',
        data: { pageTitle: 'dhis2TrackApp.indicatortype.home.title' },
        loadChildren: () => import('./indicatortype/indicatortype.module').then(m => m.IndicatortypeModule),
      },
      {
        path: 'categorycombo',
        data: { pageTitle: 'dhis2TrackApp.categorycombo.home.title' },
        loadChildren: () => import('./categorycombo/categorycombo.module').then(m => m.CategorycomboModule),
      },
      {
        path: 'optionset',
        data: { pageTitle: 'dhis2TrackApp.optionset.home.title' },
        loadChildren: () => import('./optionset/optionset.module').then(m => m.OptionsetModule),
      },
      {
        path: 'dhis-user',
        data: { pageTitle: 'dhis2TrackApp.dHISUser.home.title' },
        loadChildren: () => import('./dhis-user/dhis-user.module').then(m => m.DHISUserModule),
      },
      {
        path: 'dataelement',
        data: { pageTitle: 'dhis2TrackApp.dataelement.home.title' },
        loadChildren: () => import('./dataelement/dataelement.module').then(m => m.DataelementModule),
      },
      {
        path: 'indicator',
        data: { pageTitle: 'dhis2TrackApp.indicator.home.title' },
        loadChildren: () => import('./indicator/indicator.module').then(m => m.IndicatorModule),
      },
      {
        path: 'program',
        data: { pageTitle: 'dhis2TrackApp.program.home.title' },
        loadChildren: () => import('./program/program.module').then(m => m.ProgramModule),
      },
      {
        path: 'program-stage',
        data: { pageTitle: 'dhis2TrackApp.programStage.home.title' },
        loadChildren: () => import('./program-stage/program-stage.module').then(m => m.ProgramStageModule),
      },
      {
        path: 'dataset',
        data: { pageTitle: 'dhis2TrackApp.dataset.home.title' },
        loadChildren: () => import('./dataset/dataset.module').then(m => m.DatasetModule),
      },
      {
        path: 'program-rule',
        data: { pageTitle: 'dhis2TrackApp.programRule.home.title' },
        loadChildren: () => import('./program-rule/program-rule.module').then(m => m.ProgramRuleModule),
      },
      {
        path: 'program-rule-action',
        data: { pageTitle: 'dhis2TrackApp.programRuleAction.home.title' },
        loadChildren: () => import('./program-rule-action/program-rule-action.module').then(m => m.ProgramRuleActionModule),
      },
      {
        path: 'program-rule-variable',
        data: { pageTitle: 'dhis2TrackApp.programRuleVariable.home.title' },
        loadChildren: () => import('./program-rule-variable/program-rule-variable.module').then(m => m.ProgramRuleVariableModule),
      },
      {
        path: 'tracked-entity-attribute',
        data: { pageTitle: 'dhis2TrackApp.trackedEntityAttribute.home.title' },
        loadChildren: () => import('./tracked-entity-attribute/tracked-entity-attribute.module').then(m => m.TrackedEntityAttributeModule),
      },
      {
        path: 'option-group',
        data: { pageTitle: 'dhis2TrackApp.optionGroup.home.title' },
        loadChildren: () => import('./option-group/option-group.module').then(m => m.OptionGroupModule),
      },
      {
        path: 'organisation-unit',
        data: { pageTitle: 'dhis2TrackApp.organisationUnit.home.title' },
        loadChildren: () => import('./organisation-unit/organisation-unit.module').then(m => m.OrganisationUnitModule),
      },
      {
        path: 'program-indicator',
        data: { pageTitle: 'dhis2TrackApp.programIndicator.home.title' },
        loadChildren: () => import('./program-indicator/program-indicator.module').then(m => m.ProgramIndicatorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
