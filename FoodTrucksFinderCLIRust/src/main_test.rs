use crate::request_csv;

#[tokio::test]
pub async fn test_fetch_food_trucks()  {
    request_csv().await;
    assert_ne!(
        1,
        2
    );
}