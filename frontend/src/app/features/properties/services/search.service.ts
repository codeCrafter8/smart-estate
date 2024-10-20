import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private searchCriteriaSubject = new BehaviorSubject<any>({});
  searchCriteria$ = this.searchCriteriaSubject.asObservable();

  setSearchCriteria(criteria: any) {
    this.searchCriteriaSubject.next(criteria);
  }
}
