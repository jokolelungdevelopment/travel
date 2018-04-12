import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Journey } from './journey.model';
import { JourneyPopupService } from './journey-popup.service';
import { JourneyService } from './journey.service';
import { Trip, TripService } from '../trip';
import { City, CityService } from '../city';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-journey-dialog',
    templateUrl: './journey-dialog.component.html'
})
export class JourneyDialogComponent implements OnInit {

    journey: Journey;
    isSaving: boolean;

    trips: Trip[];

    cities: City[];

    countries: Country[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private journeyService: JourneyService,
        private tripService: TripService,
        private cityService: CityService,
        private countryService: CountryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tripService.query()
            .subscribe((res: HttpResponse<Trip[]>) => { this.trips = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cityService.query()
            .subscribe((res: HttpResponse<City[]>) => { this.cities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.countryService.query()
            .subscribe((res: HttpResponse<Country[]>) => { this.countries = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.journey.id !== undefined) {
            this.subscribeToSaveResponse(
                this.journeyService.update(this.journey));
        } else {
            this.subscribeToSaveResponse(
                this.journeyService.create(this.journey));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Journey>>) {
        result.subscribe((res: HttpResponse<Journey>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Journey) {
        this.eventManager.broadcast({ name: 'journeyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTripById(index: number, item: Trip) {
        return item.id;
    }

    trackCityById(index: number, item: City) {
        return item.id;
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-journey-popup',
    template: ''
})
export class JourneyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private journeyPopupService: JourneyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.journeyPopupService
                    .open(JourneyDialogComponent as Component, params['id']);
            } else {
                this.journeyPopupService
                    .open(JourneyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
