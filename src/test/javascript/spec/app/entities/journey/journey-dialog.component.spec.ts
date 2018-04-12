/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TravelTestModule } from '../../../test.module';
import { JourneyDialogComponent } from '../../../../../../main/webapp/app/entities/journey/journey-dialog.component';
import { JourneyService } from '../../../../../../main/webapp/app/entities/journey/journey.service';
import { Journey } from '../../../../../../main/webapp/app/entities/journey/journey.model';
import { TripService } from '../../../../../../main/webapp/app/entities/trip';
import { CityService } from '../../../../../../main/webapp/app/entities/city';
import { CountryService } from '../../../../../../main/webapp/app/entities/country';

describe('Component Tests', () => {

    describe('Journey Management Dialog Component', () => {
        let comp: JourneyDialogComponent;
        let fixture: ComponentFixture<JourneyDialogComponent>;
        let service: JourneyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [JourneyDialogComponent],
                providers: [
                    TripService,
                    CityService,
                    CountryService,
                    JourneyService
                ]
            })
            .overrideTemplate(JourneyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JourneyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JourneyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Journey(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.journey = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'journeyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Journey();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.journey = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'journeyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
