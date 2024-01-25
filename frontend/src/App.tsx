import React, { useState } from 'react';
import MyMap from './myMap';
import Trucks from './trucks';
import {
  useQuery,
  useMutation,
  useQueryClient,
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query'
function App() {

  const queryClient = new QueryClient()
  
  return (
    <QueryClientProvider client={queryClient}>
      <div className="App p-4">
        <MyMap />
        <Trucks />
      </div>
    </QueryClientProvider>


  );
}

export default App;
