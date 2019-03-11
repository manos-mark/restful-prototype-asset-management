import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Product } from './product.model';
import { PageParams } from '../projects/page-params.model';
import { FilterParams } from '../projects/filter-params.model';
import { toHttpParams } from 'src/app/shared/http-params-converter';
import { Statuses } from '../status.enum';
import { projection } from '@angular/core/src/render3';

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

    getProducts(pageParams: PageParams, filterParams: FilterParams) {

        const params = {
            field: pageParams.field,
            page: pageParams.page,
            pageSize: pageParams.pageSize,
            direction: pageParams.direction,
            fromDate: filterParams.fromDate,
            toDate: filterParams.toDate,
            statusId: filterParams.statusId,
            projectName: filterParams.projectName
        }
        return this.httpClient.get<Product[]>('api/products/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body',
            params: toHttpParams(params)
        })
    }

    updateProduct(productRequestDto, files, productId) {
        let formData = new FormData();
        let tempTypeArray = [];
        console.log(files)
        files.forEach((element) => {
            let tempTypeObj = { 
                pictureId: element.id ? element.id : 0,
                type: element.id ? "existing" : "new"
            }
            tempTypeArray.push(tempTypeObj);
            formData.append('pictures', element.file)
        });
        
        formData.append('pictureTypeRequestDto', 
            new Blob([JSON.stringify(tempTypeArray)], { type: "application/json" })
        )
        
        formData.append('productRequestDto', new Blob([JSON.stringify(productRequestDto)], {
            type: "application/json"
        }));
        
        return this.httpClient.put<any>('api/products/' + productId, formData)
    }

    updateProductStatus(product) {
        let formData = new FormData();
        let tempTypeArray = [];
        console.log(product)
        for (let i = 0; i < product.picturesCount; i++) {
            let tempTypeObj = { 
                pictureId: 0,
                type: "existing"
            }
            tempTypeArray.push(tempTypeObj);
        };
        formData.append('pictures', new Blob([JSON.stringify({})], { type: "application/json" }))
        
        formData.append('pictureTypeRequestDto', 
            new Blob([JSON.stringify(tempTypeArray)], { type: "application/json" })
        )
        
        formData.append('productRequestDto', new Blob([JSON.stringify(
            {
                productName: product.productName,
                serialNumber: product.serialNumber,
                description: product.description,
                quantity: product.quantity,
                statusId: product.status.id,
                projectId: product.projectId,
                thumbPictureIndex: -1
            }
        )], {
            type: "application/json"
        }));
        
        return this.httpClient.put<any>('api/products/' + product.id, formData)
    }

    addProduct(productRequestDto, files) {
        let formData = new FormData();

        files.forEach((element) => {
            formData.append('pictures', element.file)
        });

        formData.append('productRequestDto', new Blob([JSON.stringify(productRequestDto)], {
            type: "application/json"
        }));
        
        return this.httpClient.post<any>('api/products', formData)
    }

    deleteProduct(productId: number) {
        return this.httpClient.delete('api/products/' + productId,  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }

    getProductById(productId: number) {
        return this.httpClient.get('api/products/' + productId,  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }

    getPicturesByProductId(productId: number) {
        return this.httpClient.get<any>('api/products/' + productId + '/pictures',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }
}