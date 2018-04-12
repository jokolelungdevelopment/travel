import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ProductImage } from './product-image.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ProductImage>;

@Injectable()
export class ProductImageService {

    private resourceUrl =  SERVER_API_URL + 'api/product-images';

    constructor(private http: HttpClient) { }

    create(productImage: ProductImage): Observable<EntityResponseType> {
        const copy = this.convert(productImage);
        return this.http.post<ProductImage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(productImage: ProductImage): Observable<EntityResponseType> {
        const copy = this.convert(productImage);
        return this.http.put<ProductImage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ProductImage>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ProductImage[]>> {
        const options = createRequestOption(req);
        return this.http.get<ProductImage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ProductImage[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ProductImage = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ProductImage[]>): HttpResponse<ProductImage[]> {
        const jsonResponse: ProductImage[] = res.body;
        const body: ProductImage[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ProductImage.
     */
    private convertItemFromServer(productImage: ProductImage): ProductImage {
        const copy: ProductImage = Object.assign({}, productImage);
        return copy;
    }

    /**
     * Convert a ProductImage to a JSON which can be sent to the server.
     */
    private convert(productImage: ProductImage): ProductImage {
        const copy: ProductImage = Object.assign({}, productImage);
        return copy;
    }
}
