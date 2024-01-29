import { useState } from 'react';
import { useRequestProcessor } from './api/api';
import axiosClient from './api/axios';

import { List, Card, Input } from 'antd';
import { FoodTruck } from './enitity';

function UserList() {

    const [items, setItems] = useState([] as FoodTruck[])
    const { query } = useRequestProcessor();
    
    const { data: data, isLoading, isError } = query(
        ['trucks'],
        () => axiosClient.get('').then((res) => { 
           const d = res.data.data
           setItems(d)
           return d
        }),
        { enabled: true }
    );
    const handleSearchChange = e => {
        const query = e.target.value;
        const newItems = (data as  FoodTruck[]).filter( item => {
            if (!item.applicant) {
                item.applicant = "";
            }
            if (!item.locationid) {
                item.locationid = "";
            }
            if (!item.locationDescription) {
                item.locationDescription = "";
            }
            return  item.locationid.includes(query) ||
            item.applicant.includes(query) ||
            item.locationDescription.includes(query) 
        })
        console.log("new items length ",newItems.length)
        setItems(newItems);
    };

    function truncate(text: string, length: number) {
        if (text){
            if (text.length <= length) {
                return text;
              }
              return text.substring(0, length) + "\u2026";
        }
    }

    if (isLoading) return <p>Loading...</p>;
    if (isError) return <p>Error :(</p>;


    return (
        <div className="">
            <Input className='w-70 mt-4 mb-4' placeholder="input anything" 
                            onChange={handleSearchChange}
            />

            <List
                grid={{
                    gutter: 16,
                    xs: 1,
                    sm: 2,
                    md: 4,
                    lg: 4,
                    xl: 6,
                    xxl: 3,
                }}
                pagination={{
                    onChange: (page) => {
                        console.log(page);
                    },
                    pageSize: 10,
                }}
                dataSource={items}
                renderItem={(item ) => (
                    <List.Item>
                        <Card title={`${item.applicant}`}>
                            {`${item.locationid}-${truncate(item.foodItems,70)}`}
                        </Card>
                    </List.Item>
                )}
            />

            {/* {data.map((user) => (
                <li key={user.locationid}>{user.locationid}</li>
            ))} */}
        </div>
    );
}

export default UserList;