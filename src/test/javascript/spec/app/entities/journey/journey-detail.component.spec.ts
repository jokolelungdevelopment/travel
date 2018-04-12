/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TravelTestModule } from '../../../test.module';
import { JourneyDetailComponent } from '../../../../../../main/webapp/app/entities/journey/journey-detail.component';
import { JourneyService } from '../../../../../../main/webapp/app/entities/journey/journey.service';
import { Journey } from '../../../../../../main/webapp/app/entities/journey/journey.model';

describe('Component Tests', () => {

    describe('Journey Management Detail Component', () => {
        let comp: JourneyDetailComponent;
        let fixture: ComponentFixture<JourneyDetailComponent>;
        let service: JourneyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [JourneyDetailComponent],
                providers: [
                    JourneyService
                ]
            })
            .overrideTemplate(JourneyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JourneyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JourneyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Journey(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.journey).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
