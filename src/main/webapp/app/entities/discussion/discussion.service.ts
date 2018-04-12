import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Discussion } from './discussion.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Discussion>;

@Injectable()
export class DiscussionService {

    private resourceUrl =  SERVER_API_URL + 'api/discussions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(discussion: Discussion): Observable<EntityResponseType> {
        const copy = this.convert(discussion);
        return this.http.post<Discussion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(discussion: Discussion): Observable<EntityResponseType> {
        const copy = this.convert(discussion);
        return this.http.put<Discussion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Discussion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Discussion[]>> {
        const options = createRequestOption(req);
        return this.http.get<Discussion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Discussion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Discussion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Discussion[]>): HttpResponse<Discussion[]> {
        const jsonResponse: Discussion[] = res.body;
        const body: Discussion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Discussion.
     */
    private convertItemFromServer(discussion: Discussion): Discussion {
        const copy: Discussion = Object.assign({}, discussion);
        copy.postDate = this.dateUtils
            .convertDateTimeFromServer(discussion.postDate);
        return copy;
    }

    /**
     * Convert a Discussion to a JSON which can be sent to the server.
     */
    private convert(discussion: Discussion): Discussion {
        const copy: Discussion = Object.assign({}, discussion);

        copy.postDate = this.dateUtils.toDate(discussion.postDate);
        return copy;
    }
}
