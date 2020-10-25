import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'search-flights',
    loadChildren: () =>
      import('./flights/search/search.module').then((m) => m.SearchModule),
  },
  {
    path: '',
    redirectTo: 'search-flights',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
