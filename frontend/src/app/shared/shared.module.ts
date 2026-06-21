import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  exports: [
    // Export common modules/components that other modules will need
    CommonModule
  ]
})
export class SharedModule { }
