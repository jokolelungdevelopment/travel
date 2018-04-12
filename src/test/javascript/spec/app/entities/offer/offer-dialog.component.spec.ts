/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TravelTestModule } from '../../../test.module';
import { OfferDialogComponent } from '../../../../../../main/webapp/app/entities/offer/offer-dialog.component';
import { OfferService } from '../../../../../../main/webapp/app/entities/offer/offer.service';
import { Offer } from '../../../../../../main/webapp/app/entities/offer/offer.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { RequestService } from '../../../../../../main/webapp/app/entities/request';
import { TripService } from '../../../../../../main/webapp/app/entities/trip';

describe('Component Tests', () => {

    describe('Offer Management Dialog Component', () => {
        let comp: OfferDialogComponent;
        let fixture: ComponentFixture<OfferDialogComponent>;
        let service: OfferService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [OfferDialogComponent],
                providers: [
                    UserService,
                    RequestService,
                    TripService,
                    OfferService
                ]
            })
            .overrideTemplate(OfferDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OfferDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OfferService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Offer(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.offer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'offerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Offer();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.offer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'offerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
