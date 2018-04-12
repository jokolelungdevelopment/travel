import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Journey } from './journey.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Journey>;

@Injectable()
export class JourneyService {

    private resourceUrl =  SERVER_API_URL + 'api/journeys';

    constructor(private http: HttpClient) { }

    create(journey: Journey): Observable<EntityResponseType> {
        const copy = this.convert(journey);
        return this.http.post<Journey>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(journey: Journey): Observable<EntityResponseType> {
        const copy = this.convert(journey);
        return this.http.put<Journey>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Journey>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Journey[]>> {
        const options = createRequestOption(req);
        return this.http.get<Journey[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Journey[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Journey = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Journey[]>): HttpResponse<Journey[]> {
        const jsonResponse: Journey[] = res.body;
        const body: Journey[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Journey.
     */
    private convertItemFromServer(journey: Journey): Journey {
        const copy: Journey = Object.assign({}, journey);
        return copy;
    }

    /**
     * Convert a Journey to a JSON which can be sent to the server.
     */
    private convert(journey: Journey): Journey {
        const copy: Journey = Object.assign({}, journey);
        return copy;
    }
}
