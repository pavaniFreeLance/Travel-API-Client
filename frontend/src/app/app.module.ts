import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { SimpleTravelHttpInterceptor } from './http-interceptor/interceptor';
import { DialogComponent } from './libs/dialog/dialog.component';
import { DialogModule } from './libs/dialog/dialog.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    DialogModule,
    BrowserAnimationsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: SimpleTravelHttpInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  entryComponents: [DialogComponent],
})
export class AppModule {}
