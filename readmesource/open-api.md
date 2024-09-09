```json
{
  "openapi": "3.0.1",
  "info": {
    "title": "OpenAPI definition",
    "version": "v0"
  },
  "servers": [
    {
      "url": "http://localhost:8080",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/v1/noti-groups": {
      "post": {
        "tags": [
          "noti-group-crud-controller"
        ],
        "operationId": "save",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/NotiGroupSaveRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/NotiGroup"
                }
              }
            }
          }
        }
      }
    },
    "/v1/noti-groups/subscribe": {
      "post": {
        "tags": [
          "noti-group-crud-controller"
        ],
        "operationId": "subscribe",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/NotiGroupSubscriptionRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      }
    },
    "/v1/alerts": {
      "post": {
        "tags": [
          "noti-sending-controller"
        ],
        "operationId": "sendNoti",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/NotiSendingRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/NotiSendingResponseDto"
                }
              }
            }
          }
        }
      }
    },
    "/v1/noti-groups/unsubscribe": {
      "delete": {
        "tags": [
          "noti-group-crud-controller"
        ],
        "operationId": "unsubscribe",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/NotiGroupSubscriptionRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "NotiGroupSaveRequestDto": {
        "required": [
          "desc",
          "name"
        ],
        "type": "object",
        "properties": {
          "name": {
            "maxLength": 30,
            "minLength": 5,
            "type": "string"
          },
          "desc": {
            "maxLength": 200,
            "minLength": 5,
            "type": "string"
          }
        }
      },
      "NotiGroup": {
        "type": "object",
        "properties": {
          "id": {
            "type": "string",
            "format": "uuid"
          },
          "name": {
            "type": "string"
          },
          "desc": {
            "type": "string"
          },
          "users": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/User"
            }
          }
        }
      },
      "User": {
        "type": "object",
        "properties": {
          "id": {
            "type": "string",
            "format": "uuid"
          },
          "name": {
            "type": "string"
          },
          "token": {
            "type": "string"
          },
          "channelId": {
            "type": "string"
          }
        }
      },
      "NotiGroupSubscriptionRequestDto": {
        "required": [
          "notiGroupId",
          "userId"
        ],
        "type": "object",
        "properties": {
          "notiGroupId": {
            "type": "string",
            "format": "uuid"
          },
          "userId": {
            "type": "string",
            "format": "uuid"
          }
        }
      },
      "NotiSendingRequestDto": {
        "required": [
          "message",
          "severity",
          "target"
        ],
        "type": "object",
        "properties": {
          "target": {
            "type": "array",
            "items": {
              "type": "string"
            }
          },
          "severity": {
            "type": "string"
          },
          "message": {
            "type": "string"
          }
        }
      },
      "NotiSendingResponseDto": {
        "type": "object",
        "properties": {
          "userCount": {
            "type": "integer",
            "format": "int32"
          }
        }
      }
    }
  }
}
```