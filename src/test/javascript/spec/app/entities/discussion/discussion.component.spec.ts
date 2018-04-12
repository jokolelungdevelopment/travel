/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TravelTestModule } from '../../../test.module';
import { DiscussionComponent } from '../../../../../../main/webapp/app/entities/discussion/discussion.component';
import { DiscussionService } from '../../../../../../main/webapp/app/entities/discussion/discussion.service';
import { Discussion } from '../../../../../../main/webapp/app/entities/discussion/discussion.model';

describe('Component Tests', () => {

    describe('Discussion Management Component', () => {
        let comp: DiscussionComponent;
        let fixture: ComponentFixture<DiscussionComponent>;
        let service: DiscussionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [DiscussionComponent],
                providers: [
                    DiscussionService
                ]
            })
            .overrideTemplate(DiscussionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiscussionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiscussionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Discussion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.discussions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
