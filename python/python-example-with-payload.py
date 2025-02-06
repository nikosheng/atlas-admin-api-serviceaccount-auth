import os
from requests_oauthlib import OAuth2Session
from oauthlib.oauth2 import BackendApplicationClient
from requests.auth import HTTPBasicAuth
import requests

# Constants
token_path = "/api/oauth/token"
group_id = os.environ["PROJECT_ID"]
client_id = os.environ["MONGODB_ATLAS_CLIENT_ID"]
client_secret = os.environ["MONGODB_ATLAS_CLIENT_SECRET"]
base_url = os.getenv("MONGODB_ATLAS_BASE_URL", "https://cloud.mongodb.com")
api_url = f"/api/atlas/v2/groups/{group_id}/accessList"

def get_access_token():
    # Use BackendApplicationClient for the client credentials grant
    client = BackendApplicationClient(client_id=client_id)
    oauth = OAuth2Session(client=client)

    # Prepare Basic Auth credentials
    auth = HTTPBasicAuth(client_id, client_secret)
    token_url = f"{base_url}{token_path}"

    # Fetch the token using the proper authentication
    token = oauth.fetch_token(token_url=token_url, auth=auth)
    return token['access_token']

def fetch_data():
    access_token = get_access_token()

    payload = [{
        "cidrBlock": "10.0.0.0/16",
        "comment": "sample cidr",
        "deleteAfterDate": "2025-02-08T14:15:22Z"
    }]

    headers = {
        "Authorization": f"Bearer {access_token}",
        "Accept": "application/vnd.atlas.2023-01-01+json"
    }

    #response = requests.get(f"{base_url}{api_url}", headers=headers)
    response = requests.post(
        f"{base_url}{api_url}",
        json=payload,
        headers=headers
    )
    response.raise_for_status()

    return response.json()

if __name__ == "__main__":
    try:
        data = fetch_data()
        print(data)
    except Exception as e:
        print(f"Error: {e}")
