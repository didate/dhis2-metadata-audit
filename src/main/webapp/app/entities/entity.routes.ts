import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'dhis2AuditApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'project',
    data: { pageTitle: 'dhis2AuditApp.project.home.title' },
    loadChildren: () => import('./project/project.routes'),
  },
  {
    path: 'person-notifier',
    data: { pageTitle: 'dhis2AuditApp.personNotifier.home.title' },
    loadChildren: () => import('./person-notifier/person-notifier.routes'),
  },
  {
    path: 'indicatortype',
    data: { pageTitle: 'dhis2AuditApp.indicatortype.home.title' },
    loadChildren: () => import('./indicatortype/indicatortype.routes'),
  },
  {
    path: 'categorycombo',
    data: { pageTitle: 'dhis2AuditApp.categorycombo.home.title' },
    loadChildren: () => import('./categorycombo/categorycombo.routes'),
  },
  {
    path: 'optionset',
    data: { pageTitle: 'dhis2AuditApp.optionset.home.title' },
    loadChildren: () => import('./optionset/optionset.routes'),
  },
  {
    path: 'dhis-user',
    data: { pageTitle: 'dhis2AuditApp.dHISUser.home.title' },
    loadChildren: () => import('./dhis-user/dhis-user.routes'),
  },
  {
    path: 'dataelement',
    data: { pageTitle: 'dhis2AuditApp.dataelement.home.title' },
    loadChildren: () => import('./dataelement/dataelement.routes'),
  },
  {
    path: 'indicator',
    data: { pageTitle: 'dhis2AuditApp.indicator.home.title' },
    loadChildren: () => import('./indicator/indicator.routes'),
  },
  {
    path: 'program',
    data: { pageTitle: 'dhis2AuditApp.program.home.title' },
    loadChildren: () => import('./program/program.routes'),
  },
  {
    path: 'dataset',
    data: { pageTitle: 'dhis2AuditApp.dataset.home.title' },
    loadChildren: () => import('./dataset/dataset.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
