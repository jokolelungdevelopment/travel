/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TravelTestModule } from '../../../test.module';
import { JourneyComponent } from '../../../../../../main/webapp/app/entities/journey/journey.component';
import { JourneyService } from '../../../../../../main/webapp/app/entities/journey/journey.service';
import { Journey } from '../../../../../../main/webapp/app/entities/journey/journey.model';

describe('Component Tests', () => {

    describe('Journey Management Component', () => {
        let comp: JourneyComponent;
        let fixture: ComponentFixture<JourneyComponent>;
        let service: JourneyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [JourneyComponent],
                providers: [
                    JourneyService
                ]
            })
            .overrideTemplate(JourneyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JourneyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JourneyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Journey(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.journeys[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
