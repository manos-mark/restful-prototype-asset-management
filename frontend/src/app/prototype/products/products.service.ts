import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Product } from './product.model';
import { ProductPicture } from './product-picture.model';

@Injectable()
export class ProductsService {
    editMode = false;

    constructor(private httpClient: HttpClient) {}

    getProductsCountByStatusId(id: number) {
        return this.httpClient.post<number>('api/products/count', {
            statusId: id
        }, 
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
        
    }

    getProducts(statusId: number, field: string, page: number, pageSize: number, direction: string) {
        return this.httpClient.get<Product>('api/products/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body',
            params: new HttpParams()
                            .set('statusId', String(statusId))
                            .set('field', field) 
                            .set('page', String(page))
                            .set('pageSize', String(pageSize))
                            .set('direction', direction) 
        })
    }

    getThumbPictureByProductId(productId: number) {
        return this.httpClient.get<ProductPicture>('api/products/' + productId + '/thumb-picture',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    getPicturesCountByProductId(productId: number) {
        return this.httpClient.get<number>('api/products/' + productId + '/pictures-count',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    getPicturesByProductId(productId: number) {
        return this.httpClient.get<Array<ProductPicture>>('api/products/' + productId + '/pictures',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    addProduct(productName: string, serialNumber: string, description: string,
        quantity: number, projectId: number, ) {
        let currDate = (new Date).toLocaleString('en-GB');
        
        return this.httpClient.post<any>('api/products', 
            {
                productName: productName,
                serialNumber: serialNumber,
                description: description,
                quantity: quantity,
                projectId: projectId,
                statusId: 2,
                date: currDate
            },
            {
                headers: new HttpHeaders().set('Content-Type', 'application/json'),
                observe: 'body',
            }
        )
    }

    addPicture(productId: number, picture: ProductPicture) {
        return this.httpClient.post<any>('api/products/' + productId + '/pictures',
            {
                productId: productId,
                picture: picture.file,
                thumb: picture.thumb,
                name: picture.file.name, 
                size: picture.file.size
            },
            {
                headers: new HttpHeaders().set('Content-Type', 'application/json'),
                observe: 'body',
            }
        )
    }
}