import { Component, OnInit } from '@angular/core';
import { Property } from '../../models/property.model';
import { PropertyService } from '../../services/property.service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { ConfirmDialogComponent } from '../../../../shared/components/confirm-dialog/confirm-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { catchError, forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-my-adverts',
  standalone: true,
  imports: [CommonModule, TranslateModule, MatDialogModule, RouterModule],
  templateUrl: './my-adverts.component.html',
  styleUrl: './my-adverts.component.scss'
})
export class MyAdvertsComponent implements OnInit {
  properties: Property[] = [];
  notFoundMessage: string | null = null;

  constructor(
    private propertyService: PropertyService, 
    private router: Router, 
    private translate: TranslateService,
    private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadUserProperties();
  }

  loadUserProperties(): void {
    this.propertyService.getUserProperties().subscribe(properties => {
      this.properties = properties;
      this.notFoundMessage = properties.length === 0 
        ? this.translate.instant('NO_ADVERTS_YET') 
        : null;
    });
  }

  navigateToEditProperty(propertyId: number): void {
    this.router.navigate(['/my-adverts/edit', propertyId]);
  }

  navigateToViewInquiries(propertyId: number): void {
    this.router.navigate(['/my-adverts/inquiries', propertyId]);
  }

  navigateToDeleteProperty(property: Property, event: MouseEvent): void {
    event.stopPropagation(); 

    const translatedMessage = this.translate.instant('CONFIRM_DELETE_PROPERTY');
  
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: translatedMessage },
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const deleteImagesObservables = property.imageIds.map(imageId => 
          this.propertyService.deleteImage(imageId).pipe(
            catchError(err => {
              console.error(`Error deleting image ${imageId}`, err);
              return of(null); 
            })
          )
        );
  
        forkJoin(deleteImagesObservables).subscribe({
          next: () => {
            this.propertyService.deleteProperty(property.id).subscribe({
              next: () => {
                this.loadUserProperties(); 
              },
              error: (err) => console.error(`Error deleting property ${property.id}`, err)
            });
          },
          error: (err) => console.error('Error in deleting images', err)
        });
      }
    });
  }    
}
