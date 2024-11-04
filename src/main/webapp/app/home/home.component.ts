import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  menus: Menu[] = [];

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    if (this.account === null) {
      this.router.navigate(['/login']);
    } else {
      this.menus.push(
        {
          title: 'Element de donnees',
          description: 'Audit des modifications sur les elements de donnees',
          link: '/dataelement',
        },
        {
          title: 'Indicateurs',
          description: 'Audit des modifications sur les indicateurs',
          link: '/indicator',
        },
        {
          title: 'Ensemble des données',
          description: 'Audit des modifications sur les ensembles de donnees',
          link: '/dataset',
        },
        {
          title: 'Programmes',
          description: 'Audit des modifications sur les elements de donnees',
          link: '/program',
        },

        {
          title: 'Indicateurs de programme',
          description: 'Audit des modifications sur les indicateurs de programme',
          link: '/program-indicator',
        },
        {
          title: 'Variables de programme',
          description: 'Audit des modifications sur les variables de programme',
          link: '/program-rule-variable',
        },
        {
          title: 'Regles de programmes',
          description: 'Audit des modifications sur les regles de programme',
          link: '/program-rule',
        },
        {
          title: 'Actions de Regles de programme',
          description: 'Audit des modifications sur les actions de regles de programme',
          link: '/program-rule-action',
        },

        {
          title: "Unité d'organisation",
          description: 'Audit des modifications sur les unités organisation',
          link: '/organisation-unit',
        },
        {
          title: "Attributs d'entité",
          description: "Audit des modifications sur les attributs d'entité",
          link: '/tracked-entity-attribute',
        },
        {
          title: "Groupes d'option",
          description: "Audit des modifications sur les groupes d'option",
          link: '/option-group',
        },
        {
          title: "Types d'indicateur",
          description: "Audit des modifications sur les types d'indicateur",
          link: '/indicatortype',
        },
        {
          title: 'Combinaison de categorie',
          description: 'Audit des modifications sur les combinaisons des categories',
          link: '/categorycombo',
        },
        {
          title: 'Option Set',
          description: 'Audit des modifications sur les options set',
          link: '/optionset',
        },
        {
          title: 'Utilisateur',
          description: 'Audit des modifications sur les utilisateurs',
          link: '/dhis-user',
        }
      );
    }
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

export class Menu {
  title: string | undefined;
  description: string | undefined;
  link: string | undefined;
}
