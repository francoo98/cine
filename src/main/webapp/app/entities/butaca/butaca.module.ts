import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CineSharedModule } from 'app/shared/shared.module';
import { ButacaComponent } from './butaca.component';
import { ButacaDetailComponent } from './butaca-detail.component';
import { ButacaUpdateComponent } from './butaca-update.component';
import { ButacaDeleteDialogComponent } from './butaca-delete-dialog.component';
import { butacaRoute } from './butaca.route';

@NgModule({
  imports: [CineSharedModule, RouterModule.forChild(butacaRoute)],
  declarations: [ButacaComponent, ButacaDetailComponent, ButacaUpdateComponent, ButacaDeleteDialogComponent],
  entryComponents: [ButacaDeleteDialogComponent],
})
export class CineButacaModule {}
